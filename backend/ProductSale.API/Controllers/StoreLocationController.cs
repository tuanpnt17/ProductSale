using Microsoft.AspNetCore.Mvc;
using ProductSale.Business.StoreLocation;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StoreLocationController(IStoreLocationService locationService) : ControllerBase
    {
        [HttpGet]
        public async Task<IActionResult> GetStoreLocations()
        {
            var storeLocations = await locationService.GetStoreLocations();
            return Ok(storeLocations);
        }
    }
}
