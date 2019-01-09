using PathHunt.BusinessLayer;
using PathHunt.DataLayer;
using PathHunt.DataLayer.Model;
using PathHunt.Web.API.Controllers;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;

namespace XUnitTestPathHunt
{
    public class TeamsFacadeTest
    {
        private GameContext _gameContext = QuestionsFacadeTest._gameContext;
        private readonly TeamsFacade _teamsFacade;

        public TeamsFacadeTest()
        {
            _teamsFacade = new TeamsFacade(_gameContext);
        }

        [Theory]
        [InlineData(1, "TopTestTeam")]
        [InlineData(2, "Teletubbies")]
        [InlineData(3, "NotSoAnonymous")]
        public void TestGetTeamById(int _id, string _name)
        {
            int id = _id;
            string expectedName = _name;
            var controller = new TeamsController(_teamsFacade);
            Team result = controller.GetTeam(id);
            Assert.Equal(expectedName, result.Name);
        }

        [Fact]
        public void TestPostTeam()
        {
            Team newTeam = new Team()
            {
                Name = "TempoTeam",
                Score = 666
            };
            var controller = new TeamsController(_teamsFacade);
            controller.PostTeam(newTeam);
            var result = controller.GetTeam(4);
            Assert.Equal(newTeam.Name, result.Name);
            Assert.Equal(newTeam.Score, result.Score);

        }

    }
}
