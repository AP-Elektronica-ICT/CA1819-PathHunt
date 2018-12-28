using Microsoft.EntityFrameworkCore;
using Moq;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer;
using PathHunt.DataLayer.Model;
using PathHunt.Web.API.Controllers;
using System;
using System.Collections.Generic;
using System.Linq;
using Xunit;

namespace XUnitTestPathHunt
{
    public class QuestionsFacadeTest
    {
        public static GameContext _gameContext { get; private set; }
        private static readonly QuestionsFacade _questionsFacade;
        static QuestionsFacadeTest()
        {
            InitContext();
            _questionsFacade = new QuestionsFacade(_gameContext);
        }

        private static void InitContext()
        {
            var builder = new DbContextOptionsBuilder<GameContext>();
            InMemoryDbContextOptionsExtensions.UseInMemoryDatabase(builder, "testDB");
            var context = new GameContext(builder.Options);
            var questionsFacade = new QuestionsFacade(_gameContext);
            context.Database.EnsureCreated();
            var team1 = new Team()
            {
                Name = "TopTestTeam",
                Score = 3000
            };
            var team2 = new Team()
            {
                Name = "Teletubbies",
                Score = 101
            };
            var team3 = new Team()
            {
                Name = "NotSoAnonymous",
                Score = 4999
            };
            var location1 = new Location()
            {
                Name = "Centraal Station",
                Street = "Koningin Astridplein 27,2018 Antwerpen,Belgium"
            };
            var location2 = new Location()
            {
                Name = "David Teniers II",
                Street = "Teniersplaats 4, 2000 Antwerpen, Belgium"
            };

            var location3 = new Location()
            {
                Name = "Paleis op de Meir",
                Street = "Meir 50, 2000 Antwerpen, Belgium"
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
                Answer = "David Teniers II",
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
            context.Teams.Add(team1);
            context.Teams.Add(team2);
            context.Teams.Add(team3);
            context.SaveChanges();
            _gameContext = context;
        }

        [Fact]
        public void OldFetchQuestionsTest()
        {
            IQueryable<Question> questions = new List<Question>
                {
                    new Question
                    {
                        Content = "In welk jaar is het dit station geopend?",
                        Answer = "1905",
                        Options = new string[] { "1908", "1901", "1905" },
                        Location = new Location
                        {
                            Name = "Centraal Station",
                            Street = "Koningin Astridplein 27,2018 Antwerpen,Belgium"
                        }
                    },
                    new Question
                    {
                        Content = "Hoe heet de schilder die op dit monument is afgebeeld?",
                        Answer = "David Teniers II",
                        Options = new string[]
                        {
                            "David Teniers II", "David Teniers I", "Robert Campin"
                        },
                        Location = new Location
                        {
                            Name = "David Teniers II",
                            Street = "Teniersplaats 4, 2000 Antwerpen, Belgium"
                        }
                    },
                    new Question
                    {
                        Content = "Werkt de methode?",
                        Answer = "hopelijk",
                        Options = new string[]
                        {
                            "ja", "nee", "hopelijk"
                        },
                        Location = new Location
                        {
                            Name = "Thuis",
                            Street = "Straat 24"
                        }
                    }
                }.AsQueryable();

            var mockSet = new Mock<DbSet<Question>>();
            mockSet.As<IQueryable<Question>>().Setup(m => m.Provider).Returns(questions.Provider);
            mockSet.As<IQueryable<Question>>().Setup(m => m.Expression).Returns(questions.Expression);
            mockSet.As<IQueryable<Question>>().Setup(m => m.ElementType).Returns(questions.ElementType);
            mockSet.As<IQueryable<Question>>().Setup(m => m.GetEnumerator()).Returns(questions.GetEnumerator());

            var mockContext = new Mock<GameContext>();
            mockContext.Setup(c => c.Questions).Returns(mockSet.Object);

            var facade = new QuestionsFacade(mockContext.Object);
            var actual = facade.GetAllQuestions();
            Assert.Equal(3, actual.Count());
            Assert.Equal("1905", actual.First().Answer);
        }

        [Theory]
        [InlineData(1, "1905")]
        [InlineData(2, "David Teniers II")]
        [InlineData(3, "17e")]
        [InlineData(4, "1745")]
        public void TestGetQuestionById(int id, string answer)
        {
            string expectedAnswer = answer;
            int _id = id;
            var controller = new QuestionsController(_questionsFacade);
            Question result = controller.getQuestion(_id);
            Assert.Equal(expectedAnswer, result.Answer);
        }

        [Theory]
        [InlineData("Centraal Station")]
        [InlineData("David Teniers II")]
        [InlineData("Paleis op de Meir")]
        public void TestQuestionsByName(string _name)
        {
            string name = _name;
            List<Question> result = _questionsFacade.GetLocationQuestions(name);
            Assert.NotEmpty(result);
            Assert.Equal(name, result.First().Location.Name);
        }
    }
}
