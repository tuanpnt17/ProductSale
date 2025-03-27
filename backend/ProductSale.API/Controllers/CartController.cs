using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ProductSale.API.Models.Carts;
using ProductSale.Business.Cart;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CartController(ICartService cartService, IMapper mapper) : ControllerBase
    {
        [HttpGet("{userId}")]
        public async Task<IActionResult> GetCarts(int userId)
        {
            var cart = await cartService.GetCart(userId);
            return Ok(cart);
        }

        [HttpPost]
        public async Task<IActionResult> AddToCart(AddToCartVM addToCartVm)
        {
            await cartService.AddToCart(
                addToCartVm.UserId,
                addToCartVm.ProductId,
                addToCartVm.Quantity
            );
            var result = new { status = 200, message = "Add to cart successfully" };
            return Ok(result);
        }

        [HttpDelete("remove-item")]
        public async Task<IActionResult> RemoveItemFromCart(
            [FromQuery] int userId,
            [FromQuery] int productId
        )
        {
            await cartService.RemoveItemFromCart(userId, productId);
            var result = new { status = 200, message = "Item removed from cart successfully" };
            return Ok(result);
        }

        // Update item quantity in cart
        [HttpPut("update-item")]
        public async Task<IActionResult> UpdateCartItemQuantity(
            [FromQuery] int UserID,
            [FromQuery] int ProductID,
            [FromQuery] int Quantity
        )
        {
            await cartService.UpdateCartItemQuantity(UserID, ProductID, Quantity);
            var result = new { status = 200, message = "Cart item updated successfully" };
            return Ok(result);
        }

        // Clear the user's cart
        [HttpDelete("clear")]
        public async Task<IActionResult> ClearCart([FromQuery] int userId)
        {
            await cartService.ClearCart(userId);
            var result = new { status = 200, message = "Cart cleared successfully" };
            return Ok(result);
        }

        // Get total price of cart
        [HttpGet("total")]
        public async Task<IActionResult> GetCartTotal([FromQuery] int userId)
        {
            var total = await cartService.GetCartTotal(userId);
            return Ok(new { status = 200, total });
        }

       [HttpPost("complete-payment")]
        public async Task<IActionResult> CompletePaymentAndConvertCartToOrder([FromBody] CompletePaymentVM completePaymentVm)
        {
            await cartService.CompletePaymentAndConvertCartToOrder(completePaymentVm.UserId, completePaymentVm.PaymentMethod, completePaymentVm.BillingAddress);
            var result = new { status = 200, message = "Payment completed and cart converted to order successfully" };
            return Ok(result);
        }
    }
}
