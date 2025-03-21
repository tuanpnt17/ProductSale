using System.Linq.Expressions;
using ProductSale.Repository.Helpers;

namespace ProductSale.Repository.Interfaces
{
    public interface IGenericRepository<T>
    {
        Task<T?> GetByIdAsync(int id);
        Task<List<T>> GetAllAsync(Expression<Func<T, bool>> filter, string? includeProperties);
        Task InsertAsync(T entity);
        Task AddRangeAsync(IEnumerable<T> entities);
        void Update(T entity);
        void Delete(T entity);
        Task<T> GetFirstOrDefaultAsync(
            Expression<Func<T, bool>> predicate,
            string? includeProperties = null
        );

        Task<Pagination<T>> GetPaginationAsync(
            Expression<Func<T, bool>>? predicate = null,
            string? includeProperties = null,
            int pageIndex = 1,
            int pageSize = 10
        );
    }
}
