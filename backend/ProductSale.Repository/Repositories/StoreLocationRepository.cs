using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class StoreLocationRepository : GenericRepository<StoreLocation>, IStoreLocationRepository
{
	public StoreLocationRepository(ApplicationDbContext context) : base(context)
	{
	}
}