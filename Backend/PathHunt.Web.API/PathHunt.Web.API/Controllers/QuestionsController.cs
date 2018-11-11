using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PathHunt.BusinessLayer;
using PathHunt.DataLayer.Model;

namespace PathHunt.Web.API.Controllers
{
    public class QuestionsController : Controller
    {
        private readonly QuestionsFacade facade;
        
        public QuestionsController(QuestionsFacade _facade)
        {
            this.facade = _facade;
        }
        public IActionResult Index()
        {
            return View();
        }
        [Route("api/questions")]
        public List<Question> getQuestions()
        {
            return facade.GetAllQuestions();
        }
        [Route("api/questions/{id}")]
        public Question getQuestion(int id)
        {
            return facade.GetQuestion(id);
        }
    }
}