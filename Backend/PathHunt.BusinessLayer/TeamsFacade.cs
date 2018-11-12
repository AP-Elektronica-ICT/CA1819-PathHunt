﻿using PathHunt.DataLayer;
using PathHunt.DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Linq;

namespace PathHunt.BusinessLayer
{
    public class TeamsFacade
    {
        private readonly GameContext context;

        public TeamsFacade(GameContext _context)
        {
            this.context = _context;
        }

        public List<Team> GetAllTeams()
        {
            return context.Teams.ToList();
        }

        public Team GetTeam(int id)
        {
            return context.Teams
                .SingleOrDefault(d => d.Id == id);
        }
    }
}
