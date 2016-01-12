using Microsoft.Data.Entity;
using Microsoft.Data.Entity.Metadata;

namespace InfoInteSourceCleansing
{
    public partial class InfoInteCleansingContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            options.UseNpgsql(@"Server=sebastiankoall.de;Database=infointe;userid=infointe;password=InfoInte1516%");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<club>(entity =>
            {
                entity.Property(e => e.name)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.ligaNavigation).WithMany(p => p.club).HasForeignKey(d => d.liga);
            });

            modelBuilder.Entity<college_playing>(entity =>
            {
                entity.HasKey(e => new { e.sportsman_id, e.school_id, e.year });

                entity.Property(e => e.school_id)
                    .HasMaxLength(15)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.school).WithMany(p => p.college_playing).HasForeignKey(d => d.school_id).OnDelete(DeleteBehavior.Restrict);

                entity.HasOne(d => d.sportsman).WithMany(p => p.college_playing).HasForeignKey(d => d.sportsman_id).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<fight>(entity =>
            {
                entity.Property(e => e.date).HasColumnType("date");

                entity.Property(e => e.duration).HasColumnType("time");

                entity.Property(e => e.roundcode)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.roundname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<hall_of_fame>(entity =>
            {
                entity.HasKey(e => new { e.sportsman_id, e.year, e.voted_by });

                entity.Property(e => e.voted_by)
                    .HasMaxLength(64)
                    .HasColumnType("varchar");

                entity.Property(e => e.category)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.inducted)
                    .HasMaxLength(1)
                    .HasColumnType("varchar");

                entity.Property(e => e.needed_note)
                    .HasMaxLength(25)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.sportsman).WithMany(p => p.hall_of_fame).HasForeignKey(d => d.sportsman_id).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<league_table>(entity =>
            {
                entity.HasOne(d => d.team).WithMany(p => p.league_table).HasForeignKey(d => d.team_id);
            });

            modelBuilder.Entity<liga>(entity =>
            {
                entity.Property(e => e.erstaustragung).HasColumnType("date");

                entity.Property(e => e.rekordspieler)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.verband)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.meisterNavigation).WithMany(p => p.liga1).HasForeignKey(d => d.meister);
            });

            modelBuilder.Entity<match>(entity =>
            {
                entity.Property(e => e.date).HasColumnType("date");

                entity.Property(e => e.time).HasColumnType("time");

                entity.HasOne(d => d.foreignNavigation).WithMany(p => p.match).HasForeignKey(d => d.foreign);

                entity.HasOne(d => d.homeNavigation).WithMany(p => p.matchNavigation).HasForeignKey(d => d.home);
            });

            modelBuilder.Entity<Occupation>(entity =>
            {
                entity.Property(e => e.occupation)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<occupation_sportsman>(entity =>
            {
                entity.HasKey(e => new { e.occupation_id, e.sportsman_id });

                entity.HasOne(d => d.occupation).WithMany(p => p.occupation_sportsman).HasForeignKey(d => d.occupation_id).OnDelete(DeleteBehavior.Restrict);

                entity.HasOne(d => d.sportsman).WithMany(p => p.occupation_sportsman).HasForeignKey(d => d.sportsman_id).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<ranking>(entity =>
            {
                entity.HasOne(d => d.sportsman).WithMany(p => p.ranking).HasForeignKey(d => d.sportsman_id);
            });

            modelBuilder.Entity<school>(entity =>
            {
                entity.Property(e => e.id)
                    .HasMaxLength(15)
                    .HasColumnType("varchar");

                entity.Property(e => e.city)
                    .HasMaxLength(55)
                    .HasColumnType("varchar");

                entity.Property(e => e.country)
                    .HasMaxLength(55)
                    .HasColumnType("varchar");

                entity.Property(e => e.name_full)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.state)
                    .HasMaxLength(55)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<season>(entity =>
            {
                entity.HasOne(d => d.league_tableNavigation).WithMany(p => p.season).HasForeignKey(d => d.league_table);
            });

            modelBuilder.Entity<sportsman>(entity =>
            {
                entity.Property(e => e.Anzahl_Einsaetze).HasColumnName("Anzahl Einsaetze");

                entity.Property(e => e.bats)
                    .HasMaxLength(1)
                    .HasColumnType("varchar");

                entity.Property(e => e.bbrefID)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.Property(e => e.belt)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthCity)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthCountry)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthdate).HasColumnType("date");

                entity.Property(e => e.birthState)
                    .HasMaxLength(30)
                    .HasColumnType("varchar");

                entity.Property(e => e.coach)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.comment)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.country)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathCity)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathCountry)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathState)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.debut).HasColumnType("date");

                entity.Property(e => e.enddate)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.familynamelocal)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.favoritetechnique)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.finalGame).HasColumnType("date");

                entity.Property(e => e.firstname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.firstnamelocal)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.gender)
                    .HasMaxLength(6)
                    .HasColumnType("varchar");

                entity.Property(e => e.hand)
                    .HasMaxLength(1)
                    .HasColumnType("bpchar");

                entity.Property(e => e.lastname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.middlename)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.middlenamelocal)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.nameGiven)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.playername)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.position)
                    .HasMaxLength(30)
                    .HasColumnType("varchar");

                entity.Property(e => e.retroID)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.Property(e => e.shortname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.side)
                    .HasMaxLength(1)
                    .HasColumnType("bpchar");

                entity.Property(e => e.startdate)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.throws)
                    .HasMaxLength(1)
                    .HasColumnType("varchar");

                entity.Property(e => e.year)
                    .HasMaxLength(10)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.club).WithMany(p => p.sportsman).HasForeignKey(d => d.club_id);
            });

            modelBuilder.Entity<team>(entity =>
            {
                entity.Property(e => e.group)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.name)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");
            });
        }

        public virtual DbSet<club> club { get; set; }
        public virtual DbSet<college_playing> college_playing { get; set; }
        public virtual DbSet<fight> fight { get; set; }
        public virtual DbSet<hall_of_fame> hall_of_fame { get; set; }
        public virtual DbSet<league_table> league_table { get; set; }
        public virtual DbSet<liga> liga { get; set; }
        public virtual DbSet<match> match { get; set; }
        public virtual DbSet<Occupation> occupation { get; set; }
        public virtual DbSet<occupation_sportsman> occupation_sportsman { get; set; }
        public virtual DbSet<ranking> ranking { get; set; }
        public virtual DbSet<school> school { get; set; }
        public virtual DbSet<season> season { get; set; }
        public virtual DbSet<sportsman> sportsman { get; set; }
        public virtual DbSet<team> team { get; set; }

        // Unable to generate entity type for table 'public.team_season'. Please see the warning messages.
        // Unable to generate entity type for table 'public.team_sportsman'. Please see the warning messages.
    }
}