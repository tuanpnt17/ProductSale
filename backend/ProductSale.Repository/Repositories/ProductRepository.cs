using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class ProductRepository : GenericRepository<Product>, IProductRepository
{
	public ProductRepository(ApplicationDbContext context) : base(context)
	{
	}
}