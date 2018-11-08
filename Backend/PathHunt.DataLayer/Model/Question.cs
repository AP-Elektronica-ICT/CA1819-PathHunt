using System;
using System.Collections.Generic;
using System.Text;

namespace PathHunt.DataLayer.Model
{
    public class Question
    {
        public int Id { get; set; }
        public string Content { get; set; }
        public string Answer { get; set; }
        public Location Location { get; set; }

    }
}
