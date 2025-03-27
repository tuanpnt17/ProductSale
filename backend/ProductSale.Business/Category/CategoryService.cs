using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.Category
{
    public class CategoryService(IUnitOfWork unitOfWork) : ICategoryService
    {
        public async Task<IEnumerable<Repository.Entities.Category>> GetAllCategories()
        {
            return await unitOfWork
                .GenericRepository<Repository.Entities.Category>()
                .GetAll()
                .ToListAsync();
        }
    }
}
