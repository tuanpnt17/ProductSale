using System.Text;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using ProductSale.API.Helpers;
using ProductSale.Business.Category;
using ProductSale.Business.ChatMessage;
using ProductSale.Business.Notification;
using ProductSale.Business.Order;
using ProductSale.Business.Payment;
using ProductSale.Business.Product;
using ProductSale.Business.StoreLocation;
using ProductSale.Business.Token;
using ProductSale.Business.User;
using ProductSale.Bussiness.Cart;
using ProductSale.Repository.Data;
using ProductSale.Repository.Interfaces;
using ProductSale.Repository.Repositories;

namespace ProductSale.API
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.
            builder
                .Services.AddControllers()
                .AddJsonOptions(options =>
                {
                    options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles; //ignore json cycle
                });
            builder.Services.AddDbContext<ApplicationDbContext>(options =>
                options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection"))
            );

            // Add CORS
            builder.Services.AddCors(options =>
            {
                options.AddPolicy(
                    "AllowAndroid",
                    corsPolicyBuilder =>
                    {
                        corsPolicyBuilder.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
                    }
                );
            });

            builder.Services.AddSwaggerGen(options =>
            {
                options.AddSecurityDefinition(
                    JwtBearerDefaults.AuthenticationScheme,
                    new OpenApiSecurityScheme
                    {
                        Name = "Authorization",
                        In = ParameterLocation.Header,
                        Type = SecuritySchemeType.ApiKey,
                        Scheme = JwtBearerDefaults.AuthenticationScheme,
                    }
                );

                options.AddSecurityRequirement(
                    new OpenApiSecurityRequirement
                    {
                        {
                            new OpenApiSecurityScheme
                            {
                                Reference = new OpenApiReference
                                {
                                    Type = ReferenceType.SecurityScheme,
                                    Id = JwtBearerDefaults.AuthenticationScheme,
                                },
                                Scheme = "Oauth2",
                                Name = JwtBearerDefaults.AuthenticationScheme,
                                In = ParameterLocation.Header,
                            },
                            new List<string>()
                        },
                    }
                );
            });

            builder
                .Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(options =>
                {
                    options.TokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateIssuer = true,
                        ValidateAudience = true,
                        ValidateLifetime = true,
                        ValidateIssuerSigningKey = true,
                        ValidIssuer = builder.Configuration["Jwt:Issuer"],
                        ValidAudience = builder.Configuration["Jwt:Audience"],
                        IssuerSigningKey = new SymmetricSecurityKey(
                            Encoding.UTF8.GetBytes(builder.Configuration["Jwt:Key"])
                        ),
                    };
                });
            builder.Services.AddAuthorization();

            builder.Services.AddAutoMapper(
                opt =>
                {
                    opt.AddProfile<MappingProfile>();
                },
                typeof(AppDomain)
            );

            builder.Services.AddScoped<ICategoryRepository, CategoryRepository>();
            builder.Services.AddScoped<ICartRepository, CartRepository>();
            builder.Services.AddScoped<IChatMessageRepository, ChatMessageRepository>();
            builder.Services.AddScoped<INotificationRepository, NotificationRepository>();
            builder.Services.AddScoped<IOrderRepository, OrderRepository>();
            builder.Services.AddScoped<IPaymentRepository, PaymentRepository>();
            builder.Services.AddScoped<IProductRepository, ProductRepository>();
            builder.Services.AddScoped<IStoreLocationRepository, StoreLocationRepository>();
            builder.Services.AddScoped<IUserRepository, UserRepository>();

            builder.Services.AddScoped<ICategoryService, CategoryService>();
            builder.Services.AddScoped<ICartService, CartService>();
            builder.Services.AddScoped<IChatMessageService, ChatMessageService>();
            builder.Services.AddScoped<INotificationService, NotificationService>();
            builder.Services.AddScoped<IOrderService, OrderService>();
            builder.Services.AddScoped<IPaymentService, PaymentService>();
            builder.Services.AddScoped<IProductService, ProductService>();
            builder.Services.AddScoped<IStoreLocationService, StoreLocationService>();
            builder.Services.AddScoped<IUserService, UserService>();
            builder.Services.AddScoped<ITokenService, TokenService>();

            var app = builder.Build();
            // Swagger

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            //app.UseHttpsRedirection();

            app.UseCors("AllowAndroid");

            app.UseAuthorization();

            app.MapControllers();

            app.Run();
        }
    }
}
