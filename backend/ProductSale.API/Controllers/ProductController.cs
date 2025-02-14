using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
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
            var product = context
                .Products.Include(x => x.Category)
                .FirstOrDefault(p => p.ProductId == 2);
            return Ok(product);
            //var products = context.Categories.Include(x => x.Products).ToList();
            //return Ok(products);
        }
    }
}
