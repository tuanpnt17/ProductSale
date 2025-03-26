using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ProductSale.API.Models.Products;
using ProductSale.Business.Product;
using ProductSale.Business.StoreLocation;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController(
        IProductService productService,
        IStoreLocationService storeLocationService,
        IMapper mapper
    ) : ControllerBase
    {
        [HttpGet("demo")]
        public async Task<IActionResult> GetDemoProducts()
        {
            var allProducts = await productService.GetAllProducts();
            var productsVm = mapper.Map<IEnumerable<ProductDetailVM>>(allProducts);
            var result = new { results = productsVm.Count(), products = productsVm };
            return Ok(result);
        }

        [HttpGet("{id:int}")]
        public async Task<IActionResult> GetProduct(int id)
        {
            var product = await productService.GetProductById(id);
            if (product == null)
                return NotFound();

            var productVm = mapper.Map<ProductDetailVM>(product);
            var storeLocation = await storeLocationService.GetStoreLocations();

            var result = new { product = productVm, storeLocation };

            return Ok(result);
        }
    }
}
