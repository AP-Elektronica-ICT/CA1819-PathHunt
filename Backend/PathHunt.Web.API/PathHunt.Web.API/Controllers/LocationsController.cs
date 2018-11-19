using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer.Model;

namespace PathHunt.Web.API.Controllers
{
    public class LocationsController : Controller
    {
        private readonly LocationsFacade facade;

        public LocationsController(LocationsFacade _facade)
        {
            this.facade = _facade;
        }
        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public List<Location> GetLocations()
        {
            return facade.GetAllLocations();
        }

        [HttpGet("{id}")]
        public Location GetLocation(int id)
        {
            return facade.GetLocation(id);
        }

        /*[HttpPost]
        public postLocations(){}
         */
    }
}