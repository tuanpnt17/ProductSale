using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using ProductSale.Business.Enums;
using ProductSale.Business.Models;
using ProductSale.Business.Product;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController(IProductService productService) : ControllerBase
    {
        [Authorize(Roles = nameof(Role.Customer))]
        [HttpGet]
        public async Task<IActionResult> GetProductsAsync([FromQuery] ProductQueryDto query)
        {
            try
            {
                var products = await productService.GetProductsAsync(query);
                return Ok(products);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
