using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class CategoryRepository : GenericRepository<Category>, ICategoryRepository
{
	public CategoryRepository(ApplicationDbContext context) : base(context)
	{
	}
}