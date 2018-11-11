using System;
using System.Collections.Generic;
using System.Text;

namespace PathHunt.DataLayer.Model
{
    public class Location
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public float Longitude { get; set; }
        public float Latitude { get; set; }
        public ICollection<Question> Questions { get; set; }

    }   
}
