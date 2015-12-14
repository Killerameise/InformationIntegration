using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class league_table
    {
        public league_table()
        {
            season = new HashSet<season>();
        }

        public int id { get; set; }
        public int? awayDraws { get; set; }
        public int? awayGoals { get; set; }
        public int? awayGoalsAgainst { get; set; }
        public int? awayLosses { get; set; }
        public int? awayWins { get; set; }
        public int? draws { get; set; }
        public int? goals { get; set; }
        public int? goalsAgainst { get; set; }
        public int? goalsDifference { get; set; }
        public int? homeDraws { get; set; }
        public int? homeGoals { get; set; }
        public int? homeGoalsAgainst { get; set; }
        public int? homeLosses { get; set; }
        public int? homeWins { get; set; }
        public string leagueCaption { get; set; }
        public int? losses { get; set; }
        public int? matchday { get; set; }
        public int? playedGames { get; set; }
        public int? points { get; set; }
        public int? position { get; set; }
        public int? team_id { get; set; }
        public int? wins { get; set; }

        public virtual ICollection<season> season { get; set; }
        public virtual team team { get; set; }
    }
}
