using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ProductSale.API.Models.Categories;
using ProductSale.Business.Category;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoryController(ICategoryService categoryService, IMapper mapper)
        : ControllerBase
    {
        [HttpGet]
        public async Task<IActionResult> GetCategories()
        {
            var categories = await categoryService.GetAllCategories();
            var categoriesVm = mapper.Map<IEnumerable<CategoryVM>>(categories);
            var result = new { categories = categoriesVm };
            return Ok(result);
        }
    }
}
