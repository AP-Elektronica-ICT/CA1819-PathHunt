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
    public class LocationsFacadeTest
    {
        private GameContext _gameContext = QuestionsFacadeTest._gameContext;
        private readonly LocationsFacade _locationsFacade;
       // private LocationsController controller;

        public LocationsFacadeTest()
        {
             //controller = new LocationsController(_locationsFacade);
            _locationsFacade = new LocationsFacade(_gameContext);
        }

        [Theory]
        [InlineData(1, "Centraal Station")]
        [InlineData(2, "David Teniers II")]
        [InlineData(3, "Paleis op de Meir")]
        public void TestGetLocationById(int _id, string _name)
        {
            string expectedName = _name;
            int id = _id;
            var controller = new LocationsController(_locationsFacade);
            Location result = controller.GetLocations(_id);
            Assert.Equal(expectedName, result.Name);
        }

        [Fact]
        public void TestPostLocation()
        {
            Location newLocation = new Location()
            {
                Name = "TestLocatie",
                Street = "Bestestraat 9"
            };
            _locationsFacade.AddLocation(newLocation);
            Location result = _locationsFacade.GetAllLocations(4);
            Assert.Equal(newLocation.Street, result.Street);

        }

    }
}
