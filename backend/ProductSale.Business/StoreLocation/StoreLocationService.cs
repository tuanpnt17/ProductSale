using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.StoreLocation
{
    public class StoreLocationService(IUnitOfWork unitOfWork) : IStoreLocationService
    {
        public async Task<Repository.Entities.StoreLocation?> GetStoreLocations()
        {
            var storeLocation = await unitOfWork
                .GenericRepository<Repository.Entities.StoreLocation>()
                .GetAll()
                .FirstAsync();
            return storeLocation;
        }
    }
}
