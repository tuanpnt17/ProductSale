namespace ProductSale.Business.Token
{
    public interface ITokenService
    {
        string GenerateToken(Repository.Entities.User user);
    }
}
