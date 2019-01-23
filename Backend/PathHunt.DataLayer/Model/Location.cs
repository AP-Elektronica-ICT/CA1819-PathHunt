using System;
using System.Collections.Generic;
using System.Text;

namespace PathHunt.DataLayer.Model
{
    public class Location
    {
        public int Id { get; set; }
        public string Name { get; set; }
        
        public string Street { get; set; }

        public string ExtraStreet { get; set; }

        //public ICollection<Question> Questions { get; set; }

    }   
}
