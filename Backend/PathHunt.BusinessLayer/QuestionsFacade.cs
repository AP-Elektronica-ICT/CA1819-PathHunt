using PathHunt.DataLayer;
using PathHunt.DataLayer.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace PathHunt.BusinessLayer
{
    public class QuestionsFacade
    {
        private readonly GameContext context;

        public QuestionsFacade(GameContext _context)
        {
            this.context = _context;
        }
        public List<Question> GetAllQuestions()
        {
            return context.Questions.ToList();
        }

        public Question GetQuestion(int id)
        {
            return context.Questions
                .Include(d => d.Location)
                .SingleOrDefault(d => d.Id == id);
        }
    }
}
