using System;
using System.Collections.Generic;
using System.Linq;
using InfoInteSource;
using InfoInteTarget;
using Microsoft.Data.Entity;

namespace InfoInteBaseball
{
    class Program
    {
        static void Main(string[] args)
        {
            int i = 0;
            var sportsmanIdMap = new Dictionary<string, int>();
            var connSource = new infointesourceContext();
            var connTarget = new infointeContext();
            var transactionSource = connSource.Database.BeginTransaction();
            var transactionTarget = connTarget.Database.BeginTransaction();

            connTarget.ChangeTracker.AutoDetectChangesEnabled = false;
            connSource.ChangeTracker.AutoDetectChangesEnabled = false;
            try
            {
                var masters = connSource.master.ToList();

                masters.ForEach(m =>
                {
                    var sportsman = new sportsman
                    {
                        birthCity = m.birthcity,
                        birthCountry = m.birthcountry,
                        birthState = m.birthstate,
                        bats = m.bats,
                        debut = m.debut,
                        bbrefID = m.bbrefid,
                        weight = m.weight,
                        throws = m.throws,
                        retroID = m.retroid,
                        nameGiven = m.namegiven,
                        lastname = m.namelast,
                        firstname = m.namefirst,
                        height = m.height,
                        finalGame = m.finalgame,
                        deathCity = m.deathcity,
                        deathCountry = m.deathcountry,
                        deathDay = m.deathday,
                        deathMonth = m.deathmonth,
                        deathState = m.deathstate,
                        deathYear = m.deathyear
                    };

                    if (m.birthday != null && m.birthmonth != null && m.birthyear != null)
                    {
                        DateTime dateTime = new DateTime(m.birthyear.Value, m.birthmonth.Value, m.birthday.Value);
                        sportsman.birthdate = dateTime;
                    }

                    connTarget.sportsman.Add(sportsman);
                    connTarget.SaveChanges();
                    sportsmanIdMap.Add(m.playerid, sportsman.id);

                    foreach (var hofEntry in connSource.halloffame.Where(hof => hof.playerid == m.playerid))
                    {
                        var tmpHofEntry = new hall_of_fame()
                        {
                            ballots = hofEntry.ballots,
                            category = hofEntry.category,
                            inducted = hofEntry.inducted,
                            needed = hofEntry.needed,
                            needed_note = hofEntry.needed_note,
                            sportsman_id = sportsman.id,
                            voted_by = hofEntry.votedby,
                            votes = hofEntry.votes,
                            year = hofEntry.yearid
                        };

                        Console.WriteLine(sportsman.id.ToString());
                        connTarget.hall_of_fame.Add(tmpHofEntry);
                    }

                    connTarget.SaveChanges();

                    ++i;
                    Console.WriteLine("Inserted Masters: " + i.ToString());

                    if (i % 250 == 0)
                    {
                        transactionSource.Commit();
                        transactionTarget.Commit();
                        transactionSource = connSource.Database.BeginTransaction();
                        transactionTarget = connTarget.Database.BeginTransaction();
                    }
                });

                i = 0;
                var schools = connSource.schools.ToList();

                schools.ForEach(s =>
                {
                    var school = new school()
                    {
                        id = s.schoolid,
                        city = s.city,
                        name_full = s.name_full,
                        country = s.country,
                        state = s.state
                    };

                    connTarget.school.Add(school);

                    ++i;
                    Console.WriteLine("Inserted Schools: " + i.ToString());

                    connTarget.SaveChanges();
                    if (i % 250 == 0)
                    {
                        transactionSource.Commit();
                        transactionTarget.Commit();
                        transactionSource = connSource.Database.BeginTransaction();
                        transactionTarget = connTarget.Database.BeginTransaction();
                    }
                });

                i = 0;
                var collegePlayings = connSource.collegeplaying.ToList();

                collegePlayings.ForEach(cp =>
                {
                    var collegePlaying = new college_playing()
                    {
                        year = cp.yearid,
                        school_id = cp.schoolid,
                        sportsman_id = sportsmanIdMap[cp.playerid]
                    };

                    connTarget.Add(collegePlaying);

                    ++i;
                    Console.WriteLine("Inserted College: " + i.ToString());

                    connTarget.SaveChanges();
                    if (i % 250 == 0)
                    {
                        transactionSource.Commit();
                        transactionTarget.Commit();
                        transactionSource = connSource.Database.BeginTransaction();
                        transactionTarget = connTarget.Database.BeginTransaction();
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
