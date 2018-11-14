using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer.Model;

namespace PathHunt.Web.API.Controllers
{
    [Route("api/[controller]")]
    public class TeamsController : Controller
    {
        private readonly TeamsFacade facade;

        public TeamsController(TeamsFacade facade)
        {
            this.facade = facade;
        }

        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public List<Team> GetTeams()
        {
            return facade.GetAllTeams();
        }

        [HttpGet("{id}")]
        public Team GetTeam(int id)
        {
            return facade.GetTeam(id);
        }

        [HttpPost]
        public IActionResult PostTeam([FromBody]Team team)
        {
            facade.SetTeam(team);
            return Created("", team);
        }
    }
}