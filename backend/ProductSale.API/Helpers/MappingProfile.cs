using AutoMapper;
using ProductSale.API.Models.Products;

namespace ProductSale.API.Helpers
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            #region // ======= Category =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();
            #endregion

            #region // ======= Cart =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();
            #endregion

            #region // ======= Chat Message =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Notification =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Order =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Payment =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Product =======
            CreateMap<Repository.Entities.Product, ProductDetailVM>()
                .ForMember(
                    dest => dest.CategoryName,
                    opt =>
                        opt.MapFrom(src => src.Category == null ? "N/A" : src.Category.CategoryName)
                )
                .ReverseMap();
            #endregion

            #region // ======= Store Location =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= User =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion
        }
    }
}
