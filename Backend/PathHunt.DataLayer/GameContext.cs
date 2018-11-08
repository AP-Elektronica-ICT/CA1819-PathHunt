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

        public DbSet<Question> Questions { get; set; }
        public DbSet<Location> Locations { get; set; }
        public DbSet<Team> Teams { get; set; }
    }
}
