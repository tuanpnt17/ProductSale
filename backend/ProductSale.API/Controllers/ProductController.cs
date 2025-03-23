using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ProductSale.API.Models.Products;
using ProductSale.Business.Product;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController(IProductService productService, IMapper mapper) : ControllerBase
    {
        [HttpGet]
        public async Task<IActionResult> GetProducts()
        {
            var products = await productService.GetAllProducts();
            var productsVm = mapper.Map<IEnumerable<ProductDetailVM>>(products);
            return Ok(productsVm);
        }

        [HttpGet("{id:int}")]
        public async Task<IActionResult> GetProduct(int id)
        {
            var product = await productService.GetProductById(id);
            if (product == null)
                return NotFound();

            var productVm = mapper.Map<ProductDetailVM>(product);

            return Ok(productVm);
        }
    }
}
