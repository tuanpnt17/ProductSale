using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.Cart
{
    public class CartService(IUnitOfWork unitOfWork) : ICartService
    {
        public async Task<Repository.Entities.Cart?> GetCart(int userId)
        {
            var cart = await unitOfWork
                .GenericRepository<Repository.Entities.Cart>()
                .GetAll()
                .Include(c => c.User)
                .Include(c => c.CartItems)
                .ThenInclude(x => x.Product)
                .FirstOrDefaultAsync(x => x.UserId == userId);
            return cart;
        }

        public async Task AddToCart(int userId, int productId, int quantity)
        {
            var cart = await GetCart(userId);
            if (cart == null)
            {
                cart = new Repository.Entities.Cart
                {
                    UserId = userId,
                    Status = "Pending",
                    TotalPrice = 0,
                    CartItems = new List<CartItem>(),
                };
                await unitOfWork.GenericRepository<Repository.Entities.Cart>().InsertAsync(cart);
            }

            var cartItem = cart.CartItems.FirstOrDefault(ci => ci.ProductId == productId);
            if (cartItem == null)
            {
                cartItem = new CartItem
                {
                    ProductId = productId,
                    Quantity = quantity,
                    Price =
                        0 // Assume price will be set later
                    ,
                };
                cart.CartItems.Add(cartItem);
            }
            else
            {
                cartItem.Quantity += quantity;
            }

            cart.TotalPrice += cartItem.Price * quantity;
            unitOfWork.GenericRepository<Repository.Entities.Cart>().Update(cart);
            await unitOfWork.SaveChangesAsync();
        }

        public async Task RemoveItemFromCart(int userId, int productId)
        {
            var cart = await GetCart(userId);
            if (cart == null)
                return;

            var cartItem = cart.CartItems.FirstOrDefault(ci => ci.ProductId == productId);
            if (cartItem != null)
            {
                cart.TotalPrice -= cartItem.Price * cartItem.Quantity;
                cart.CartItems.Remove(cartItem);
                unitOfWork.GenericRepository<Repository.Entities.Cart>().Update(cart);
                await unitOfWork.SaveChangesAsync();
            }
        }

        public async Task UpdateCartItemQuantity(int userId, int productId, int quantity)
        {
            var cart = await GetCart(userId);
            if (cart == null)
                return;

            var cartItem = cart.CartItems.FirstOrDefault(ci => ci.ProductId == productId);
            if (cartItem != null)
            {
                // Update total price
                cart.TotalPrice -= cartItem.Price * cartItem.Quantity;
                cartItem.Quantity = quantity;
                cart.TotalPrice += cartItem.Price * cartItem.Quantity;

                unitOfWork.GenericRepository<Repository.Entities.Cart>().Update(cart);
                await unitOfWork.SaveChangesAsync();
            }
        }

        public async Task ClearCart(int userId)
        {
            var cart = await GetCart(userId);
            if (cart == null)
                return;

            cart.CartItems.Clear();
            cart.TotalPrice = 0;
            unitOfWork.GenericRepository<Repository.Entities.Cart>().Update(cart);
            await unitOfWork.SaveChangesAsync();
        }

        public async Task<decimal> GetCartTotal(int userId)
        {
            var cart = await GetCart(userId);
            if (cart == null)
                return 0;

            decimal total = 0;
            foreach (var cartItem in cart.CartItems)
            {
                total += cartItem.Price * cartItem.Quantity;
            }

            return total;
        }

        public async Task CompletePaymentAndConvertCartToOrder(
            int userId,
            string PaymentMethod,
            string BillingAddress
        )
        {
            var cart = await unitOfWork
                .GenericRepository<Repository.Entities.Cart>()
                .GetAll()
                .Include(c => c.CartItems)
                .FirstOrDefaultAsync(x => x.UserId == userId && x.Status == "Pending");

            if (cart == null || cart.CartItems.Count == 0)
                return;
            cart.Status = "Completed";

            var order = new Repository.Entities.Order
            {
                UserId = userId,
                CartId = cart.CartId,
                OrderStatus = "Processing",
                OrderDate = DateTime.Now,
                PaymentMethod = PaymentMethod,
                BillingAddress = BillingAddress,
            };
            await unitOfWork.GenericRepository<Repository.Entities.Order>().InsertAsync(order);

            var payment = new Repository.Entities.Payment
            {
                OrderId = order.OrderId,
                Amount = cart.TotalPrice,
                PaymentStatus = "Completed",
                PaymentDate = DateTime.Now,
            };
            await unitOfWork.GenericRepository<Repository.Entities.Payment>().InsertAsync(payment);

            await unitOfWork.SaveChangesAsync();

            unitOfWork.GenericRepository<Repository.Entities.Cart>().Update(cart);
            await unitOfWork.SaveChangesAsync();
        }
    }
}
