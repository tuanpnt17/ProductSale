using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using ProductSale.API.Models.Products;
using ProductSale.Business.Enums;
using ProductSale.Business.Models;
using ProductSale.Business.Product;
using ProductSale.Repository.Helpers;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController(IProductService productService, IMapper mapper) : ControllerBase
    {
        //[Authorize(Roles = nameof(Role.Customer))]
        [HttpGet]
        public async Task<IActionResult> GetProductsAsync([FromQuery] ProductQueryDto query)
        {
            try
            {
                var products = await productService.GetProductsAsync(query);
                var productsVm = new Pagination<ProductSummaryVM>()
                {
                    TotalItemsCount = products.TotalItemsCount,
                    PageSize = products.PageSize,
                    PageIndex = products.PageIndex,
                    Items = mapper.Map<ICollection<ProductSummaryVM>>(products.Items),
                };
                return Ok(productsVm);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

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

            return Ok(productVm);
        }
    }
}
