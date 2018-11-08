using System;
using System.Collections.Generic;
using System.Text;

namespace PathHunt.DataLayer.Model
{
    public class Location
    {
        public int Id { get; set; }
        public float Longitude { get; set; }
        public float Latitutde { get; set; }
        public ICollection<Question> Questions { get; set; }

    }   
}
