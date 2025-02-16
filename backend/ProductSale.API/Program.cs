using System.Text.Json.Serialization;
using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Data;

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

            var app = builder.Build();

            // Configure the HTTP request pipeline.

            //app.UseHttpsRedirection();

            app.UseCors("AllowAndroid");

            app.UseAuthorization();

            app.MapControllers();

            app.Run();
        }
    }
}
