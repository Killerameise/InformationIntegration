using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class master
    {
        public master()
        {
            collegeplaying = new HashSet<collegeplaying>();
            halloffame = new HashSet<halloffame>();
            salaries = new HashSet<salaries>();
        }

        public string playerid { get; set; }
        public string bats { get; set; }
        public string bbrefid { get; set; }
        public string birthcity { get; set; }
        public string birthcountry { get; set; }
        public int? birthday { get; set; }
        public int? birthmonth { get; set; }
        public string birthstate { get; set; }
        public int? birthyear { get; set; }
        public string deathcity { get; set; }
        public string deathcountry { get; set; }
        public int? deathday { get; set; }
        public int? deathmonth { get; set; }
        public string deathstate { get; set; }
        public int? deathyear { get; set; }
        public DateTime? debut { get; set; }
        public DateTime? finalgame { get; set; }
        public double? height { get; set; }
        public string namefirst { get; set; }
        public string namegiven { get; set; }
        public string namelast { get; set; }
        public string retroid { get; set; }
        public string throws { get; set; }
        public int? weight { get; set; }

        public virtual ICollection<collegeplaying> collegeplaying { get; set; }
        public virtual ICollection<halloffame> halloffame { get; set; }
        public virtual ICollection<salaries> salaries { get; set; }
    }
}
