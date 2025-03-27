namespace ProductSale.Business.Category
{
    public interface ICategoryService
    {
        Task<IEnumerable<Repository.Entities.Category>> GetAllCategories();
    }
}
