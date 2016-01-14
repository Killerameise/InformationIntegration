using System;
using Microsoft.Data.Entity;
using System.Collections.Generic;
using System.Globalization;
using InfoInteSourceCleansing;
using Npgsql;

namespace InfoInteCleansing
{
    class Program
    {
        static void Main(string[] args)
        {
            FixSportsman();

            Console.ReadKey();
        }

        private static void FixSportsman()
        {
            var connTarget = new InfoInteCleansingContext();

            var i = 0;
            using (var conn = new InfoInteCleansingContext())
            {
                conn.Database.SetCommandTimeout(500);

                foreach (var sportsman in conn.sportsman)
                {
                    var entryChanged = false;

                    if (sportsman.firstname != null && sportsman.firstname.EndsWith(" 0") && sportsman.firstname.Length > 1)
                    {
                        // Cut of " 0"  
                        if (" 0".Equals(sportsman.firstname))
                        {
                            sportsman.firstname = null;
                        }
                        else
                        {
                            sportsman.firstname = sportsman.firstname.Substring(0, sportsman.firstname.Length - 3);
                        }
                        entryChanged = true;
                    }

                    if (sportsman.lastname != null && sportsman.lastname.ToUpper() == sportsman.lastname && sportsman.lastname.Length > 1)
                    {
                        sportsman.lastname = UppercaseWords(sportsman.lastname.ToLower());

                        if (sportsman.lastname.ToUpper() != sportsman.lastname)
                        {
                            entryChanged = true;
                        }
                    }

                    ++i;
                    if (entryChanged)
                    {
                        Console.WriteLine("Change at: " + i + " id: " + sportsman.id);
                        connTarget.sportsman.Update(sportsman);
                    }

                    if (i % 50 == 0)
                    {
                        connTarget.SaveChanges();
                    }
                }
            }

        }

        // Source: http://www.dotnetperls.com/uppercase-first-letter    
        private static string UppercaseWords(string value)
        {
            var array = value.ToCharArray();
            // Handle the first letter in the string.
            if (array.Length >= 1 && char.IsLower(array[0]))
            {
                array[0] = char.ToUpper(array[0]);
            }

            // Scan through the letters, checking for spaces.
            // ... Uppercase the lowercase letters following spaces.
            for (var i = 1; i < array.Length; i++)
            {
                if (array[i - 1] == ' ')
                {
                    if (char.IsLower(array[i]))
                    {
                        array[i] = char.ToUpper(array[i]);
                    }
                }
            }
            return new string(array);
        }
    }
}
