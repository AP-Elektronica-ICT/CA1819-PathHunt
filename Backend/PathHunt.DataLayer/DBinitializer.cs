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
                    Email = "a_team@gmail.com"
                };
                context.Teams.Add(Team1);
                var Team2 = new Team()
                {
                    Name = "Test Team",
                    Score = 800,
                    Email = "testteam@gmail.com"
                };
                context.Teams.Add(Team2);
                //context.SaveChanges();
            }

            if (!context.Locations.Any())
            {

            }

            if (!context.Questions.Any())
            {
                var location1 = new Location()
                {
                    Name = "Locatie 1",
                    Latitude = 51.231268,
                    Longitude = 4.406029
                };
                var location2 = new Location()
                {
                    Name = "Locatie 2",
                    Latitude = 51.227148,
                    Longitude = 4.407901
                };
                var Question1 = new Question()
                {
                    Content = "Waar is dit?",
                    Answer = "Antwerpen",
                    Location = location1
                };
                var Question2 = new Question()
                {
                    Content = "Van welk jaar is dit monument?",
                    Answer =  "1889",
                    Location = location2
                };
                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Questions.Add(Question1);
                context.Questions.Add(Question2);
                context.SaveChanges();
            }

            
        }
    }
}
