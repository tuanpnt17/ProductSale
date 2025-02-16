using Microsoft.AspNetCore.Mvc;
using ProductSale.Repository.Data;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController(ApplicationDbContext context) : ControllerBase
    {
        [HttpGet]
        public IActionResult GetProducts()
        {
            var product = context.Products.ToList();
            return Ok(product);
        }
    }
}
