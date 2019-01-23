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
            }
                context.SaveChanges();

            if (!context.Questions.Any())
            {
                var location = new Location()
                {
                    Name = "Van Oevelen",
                    Street = "Moerkantsebaan 47, 2910 Essen, Belgium",
                    ExtraStreet = ""
                };

                var location1 = new Location()
                {
                    Name = "Brico",
                    Street = "Heikantstraat 129, 2910 Essen, Belgium",
                    ExtraStreet = "Moerkantsebaan 52, 2910 Essen, Belgium"
                };
                var location2 = new Location()
                {
                    Name = "Centraal Station",
                    Street = "Koningin Astridplein 27,2018 Antwerpen,Belgium",
                    ExtraStreet = ""
                };
                var location3 = new Location()
                {
                    Name = "David Teniers II",
                    Street = "Teniersplaats 4, 2000 Antwerpen, Belgium",
                    ExtraStreet = ""
                };

                var location4 = new Location()
                {
                    Name = "Paleis op de Meir",
                    Street ="Meir 50, 2000 Antwerpen, Belgium",
                    ExtraStreet = ""
                };

                var Question1 = new Question()
                {
                    Content = "In welk jaar is het dit station geopend?",
                    Answer = "1905",
                    Options = new string[] { "1908", "1901", "1905" },
                    Location = location2
                };
                var Question2 = new Question()
                {
                    Content = "Hoe heet de schilder die op dit monument is afgebeeld?",
                    Answer =  "David Teniers II",
                    Options = new string[]
                    {
                        "David Teniers II", "David Teniers I", "Robert Campin"
                    },
                    Location = location3
                };
                var Question3 = new Question()
                {
                    Content = "In welke eeuw is deze schilder geboren?",
                    Answer = "17e",
                    Options = new string[]
                    {
                        "18e", "17e", "16e"
                    },
                    Location = location3
                };
                var Question4 = new Question()
                {
                    Content = "In welk jaar is dit paleis gebouwd?",
                    Answer = "1745",
                    Options = new string[]
                    {
                        "1690", "1710", "1745"
                    },
                    Location = location4
                };
                var Question5 = new Question()
                {
                    Content = "Welke koning liet er de Spiegelzaal aanleggen?",
                    Answer = "Leopold II",
                    Options = new string[]
                    {
                        "Leopold II", "Napoleon Bonaparte", "Willem I"
                    },
                    Location = location4
                };

                var Question6 = new Question()
                {
                    Content = "Zijn we bij Van Oevelen?",
                    Answer = "Ja",
                    Options = new string[]
                    {
                        "Ja", "Nee", "Misschien"
                    },
                    Location = location
                };

                var Question7 = new Question()
                {
                    Content = "Zijn we bij Brico?",
                    Answer = "Ja",
                    Options = new string[]
                    {
                        "Ja", "Nee", "Misschien"
                    },
                    Location = location1
                };

                context.Locations.Add(location);
                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Locations.Add(location3);
                context.Locations.Add(location4);
                context.Questions.Add(Question1);
                context.Questions.Add(Question2);
                context.Questions.Add(Question3);
                context.Questions.Add(Question4);
                context.Questions.Add(Question5);
                context.Questions.Add(Question6);
                context.Questions.Add(Question7);
                context.SaveChanges();
            }

            
        }
    }
}
