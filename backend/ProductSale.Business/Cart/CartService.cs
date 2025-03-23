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
                    Status = "Active",
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
    }
}
