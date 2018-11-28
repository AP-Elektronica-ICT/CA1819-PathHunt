using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.EntityFrameworkCore;

namespace PathHunt.Web.API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddDbContext<GameContext>(options => options.UseSqlServer(
                Configuration.GetConnectionString("DefaultConnection")
                )
            );

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAllHeaders", builder => {
                    builder.AllowAnyHeader();
                    builder.AllowAnyMethod();
                    builder.AllowAnyOrigin();
                });
            });

            services.AddScoped<QuestionsFacade>();
            services.AddScoped<TeamsFacade>();
            services.AddScoped<LocationsFacade>();
            services.AddMvc();
            services.AddCors();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, GameContext ctext)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseMvc();
            app.UseCors("AllowAllHeaders");

            DBInitializer.Initialize(ctext);
        }
    }
}
