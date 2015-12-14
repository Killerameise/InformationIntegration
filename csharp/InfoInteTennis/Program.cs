using System;
using System.Globalization;
using System.Linq;
using InfoInteSource;
using InfoInteTarget;
using Microsoft.Data.Entity;

namespace InfoInteTennis
{
    class Program
    {
        static void Main(string[] args)
        {
            int i = 0;
            var connSource = new infointesourceContext();
            var connTarget = new infointeContext();
            var transactionTarget = connTarget.Database.BeginTransaction();
            var transactionSource = connSource.Database.BeginTransaction();

            connTarget.ChangeTracker.AutoDetectChangesEnabled = false;
            connSource.ChangeTracker.AutoDetectChangesEnabled = false;
            try
            {
                var players = connSource.players.ToList();

                players.ForEach(sm =>
                {
                    var sportsman = new sportsman
                    {
                        firstname = sm.firstname,
                        lastname = sm.lastname,
                        country = sm.country,
                        hand = sm.hand,
                    };

                    DateTime dateTime;
                    if (DateTime.TryParseExact(sm.birth.ToString(), "yyyyMMdd", new CultureInfo("de-DE"),
                        DateTimeStyles.None, out dateTime))
                    {
                        sportsman.birthdate = dateTime;
                    }

                    connTarget.sportsman.Add(sportsman);
                    connTarget.SaveChanges();

                    foreach (var ranking in connSource.rankings.Where(r => r.player_id == sm.id))
                    {
                        var tmpRanking = new ranking
                        {
                            date = ranking.date,
                            sportsman_id = sportsman.id,
                            position = ranking.pos,
                            pts = ranking.pts
                        };

                        connTarget.ranking.Add(tmpRanking);
                    }

                    connTarget.SaveChanges();

                    ++i;
                    Console.WriteLine("Inserted: " + i.ToString());

                    if (i%250 == 0)
                    {
                        transactionTarget.Commit();
                        transactionSource.Commit();

                        transactionTarget = connTarget.Database.BeginTransaction();
                        transactionSource = connSource.Database.BeginTransaction();
                    }
                });

                transactionTarget.Commit();
                transactionSource.Commit();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.InnerException.Message);
                transactionTarget.Rollback();
                transactionSource.Rollback();
            }
            Console.ReadKey();
        }
    }
}
