using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer.Model;

namespace PathHunt.Web.API.Controllers
{
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

        [Route("api/teams")]
        public List<Team> getTeams()
        {
            return facade.GetAllTeams();
        }

        [Route("api/teams/{id}")]
        public Team getTeam(int id)
        {
            return facade.GetTeam(id);
        }

        [Route("api/teams")]
        public void postTeam(Team team)
        {

        }
    }
}