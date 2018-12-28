using Microsoft.EntityFrameworkCore;
using PathHunt.DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace PathHunt.DataLayer
{
    public class GameContext : DbContext
    {
        public GameContext(DbContextOptions<GameContext> options) : base(options)
        {
        }

        public GameContext() {}

        //https://kimsereyblog.blogspot.com/2017/12/save-array-of-string-entityframework.html
        //String[] kan niet gemapt worden, in de database zetten als string met ; bv: a;b;c
        //wordt [a, b, c]  indien opgehaald
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Question>()
                    .Property<string>("OptionsCollection")
                    .HasField("_options");
        }
        public virtual DbSet<Question> Questions { get; set; }
        public virtual DbSet<Location> Locations { get; set; }
        public virtual DbSet<Team> Teams { get; set; }
    }
}
