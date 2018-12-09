using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace PathHunt.DataLayer.Model
{
    public class Question
    {
        private static readonly char delimiter = ';';
        public int Id { get; set; }
        public string Content { get; set; }
        public string Answer { get; set; }
        private string _options;
        [NotMapped]
        public string[] Options
        {
            get { return _options.Split(delimiter); }
            set
            {
                _options = string.Join($"{delimiter}", value);
            }
        }

        [JsonIgnore]
        public Location Location { get; set; }

    }
}
