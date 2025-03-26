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
        [HttpGet]
        public async Task<IActionResult> GetCarts(int userId)
        {
            var cart = await cartService.GetCart(userId);
            return Ok(cart);
        }

        [HttpPost]
        public async Task<IActionResult> AddToCart(AddToCartVM addToCartVM)
        {
            await cartService.AddToCart(
                addToCartVM.UserId,
                addToCartVM.ProductId,
                addToCartVM.Quantity
            );
            return Ok();
        }
    }
}
