using PathHunt.DataLayer.Model;
using System;
using System.Linq;

namespace PathHunt.DataLayer
{
    public class DBInitializer
    {
        public static void Initialize(GameContext context)
        {
            //Create the db if not yet exists
            context.Database.EnsureCreated();

            if (!context.Teams.Any())
            {
                var Team1 = new Team()
                {
                    Name = "The A-Team",
                    Score = 0,
                    //Email = "a_team@gmail.com"
                };
                context.Teams.Add(Team1);
                var Team2 = new Team()
                {
                    Name = "Test Team",
                    Score = 800,
                    //Email = "testteam@gmail.com"
                };
                context.Teams.Add(Team2);
                //context.SaveChanges();
            }

            if (!context.Locations.Any())
            {
                var location3 = new Location()
                {
                    Name="MAS",
                    Latitude = 51.2289238F,
                    Longitude = 4.4026316F
                };

                var location4 = new Location()
                {  
                    Name="US Army Monument",
                    Latitude = 51.2256383F,
                    Longitude = 4.3989945F

                };

                context.Locations.Add(location3);
                context.Locations.Add(location4);
                //context.SaveChanges();

            }

            if (!context.Questions.Any())
            {
                var location1 = new Location()
                {
                    Name = "Centraal Station",
                    Latitude = 51.21659257523276F,
                    Longitude = 4.4211409093259135F
                };
                var location2 = new Location()
                {
                    Name = "David Teniers II",
                    Latitude = 51.21815464148643F,
                    Longitude = 4.411614649205831F
                };

                var location3 = new Location()
                {
                    Name = "Paleis op de Meir",
                    Latitude = 51.21811763366044F,
                    Longitude = 4.408473496461511F,
                };
                var Question1 = new Question()
                {
                    Content = "In welk jaar is het dit station geopend?",
                    Answer = "1905",
                    Options = new string[] { "1908", "1901", "1905" },
                    Location = location1
                };
                var Question2 = new Question()
                {
                    Content = "Hoe heet de schilder die op dit monument is afgebeeld?",
                    Answer =  "David Teniers II",
                    Options = new string[]
                    {
                        "David Teniers II", "David Teniers I", "Robert Campin"
                    },
                    Location = location2
                };
                var Question3 = new Question()
                {
                    Content = "In welke eeuw is deze schilder geboren?",
                    Answer = "17e",
                    Options = new string[]
                    {
                        "18e", "17e", "16e"
                    },
                    Location = location2
                };
                var Question4 = new Question()
                {
                    Content = "In welk jaar is dit paleis gebouwd?",
                    Answer = "1745",
                    Options = new string[]
                    {
                        "1690", "1710", "1745"
                    },
                    Location = location3
                };
                var Question5 = new Question()
                {
                    Content = "Welke koning liet er de Spiegelzaal aanleggen?",
                    Answer = "Leopold II",
                    Options = new string[]
                    {
                        "Leopold II", "Napoleon Bonaparte", "Willem I"
                    },
                    Location = location3
                };
                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Locations.Add(location3);
                context.Questions.Add(Question1);
                context.Questions.Add(Question2);
                context.Questions.Add(Question3);
                context.Questions.Add(Question4);
                context.Questions.Add(Question5);
                context.SaveChanges();
            }

            
        }
    }
}
