using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class salaries
    {
        public int yearid { get; set; }
        public string teamid { get; set; }
        public string lgid { get; set; }
        public string playerid { get; set; }
        public double? salary { get; set; }

        public virtual master playeridNavigation { get; set; }
    }
}
