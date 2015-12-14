using Microsoft.Data.Entity;
using Microsoft.Data.Entity.Metadata;

namespace InfoInteSource
{
    public partial class infointesourceContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            options.UseNpgsql(@"Server=sebastiankoall.de;Database=infointesource;userid=infointesource;password=InfoInte1516$");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<article_header>(entity =>
            {
                entity.HasKey(e => e.article_id);

                entity.Property(e => e.article_id)
                    .HasMaxLength(24)
                    .HasColumnType("varchar");

                entity.Property(e => e._abstract).HasColumnName("abstract");

                entity.Property(e => e.document_type)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.print_page)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.pub_date).HasColumnType("date");

                entity.Property(e => e.source)
                    .HasMaxLength(250)
                    .HasColumnType("varchar");

                entity.Property(e => e.web_url)
                    .HasMaxLength(1000)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<collegeplaying>(entity =>
            {
                entity.HasKey(e => new { e.playerid, e.schoolid, e.yearid });

                entity.Property(e => e.playerid)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.Property(e => e.schoolid)
                    .HasMaxLength(15)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.playeridNavigation).WithMany(p => p.collegeplaying).HasForeignKey(d => d.playerid).OnDelete(DeleteBehavior.Restrict);

                entity.HasOne(d => d.schoolidNavigation).WithMany(p => p.collegeplaying).HasForeignKey(d => d.schoolid).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<halloffame>(entity =>
            {
                entity.HasKey(e => new { e.playerid, e.yearid, e.votedby });

                entity.Property(e => e.playerid)
                    .HasMaxLength(10)
                    .HasColumnType("varchar");

                entity.Property(e => e.votedby)
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

                entity.HasOne(d => d.playeridNavigation).WithMany(p => p.halloffame).HasForeignKey(d => d.playerid).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<master>(entity =>
            {
                entity.HasKey(e => e.playerid);

                entity.Property(e => e.playerid)
                    .HasMaxLength(10)
                    .HasColumnType("varchar");

                entity.Property(e => e.bats)
                    .HasMaxLength(1)
                    .HasColumnType("varchar");

                entity.Property(e => e.bbrefid)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthcity)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthcountry)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.birthstate)
                    .HasMaxLength(30)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathcity)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathcountry)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.deathstate)
                    .HasMaxLength(20)
                    .HasColumnType("varchar");

                entity.Property(e => e.debut).HasColumnType("date");

                entity.Property(e => e.finalgame).HasColumnType("date");

                entity.Property(e => e.namefirst)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.namegiven)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.namelast)
                    .HasMaxLength(50)
                    .HasColumnType("varchar");

                entity.Property(e => e.retroid)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.Property(e => e.throws)
                    .HasMaxLength(1)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<organizations>(entity =>
            {
                entity.HasKey(e => e.organization_id);

                entity.Property(e => e.organization_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.attribution_name)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.attribution_url)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.created).HasColumnType("date");

                entity.Property(e => e.creator)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.first_use).HasColumnType("date");

                entity.Property(e => e.in_scheme)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.latest_use).HasColumnType("date");

                entity.Property(e => e.license)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.mapping_strategy)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.modified).HasColumnType("date");

                entity.Property(e => e.pref_label)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.primary_topic)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.rightsholder)
                    .HasMaxLength(200)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<organizations_ref>(entity =>
            {
                entity.HasKey(e => new { e.article_id, e.organization_id });

                entity.Property(e => e.article_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.organization_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.article).WithMany(p => p.organizations_ref).HasForeignKey(d => d.article_id).OnDelete(DeleteBehavior.Restrict);

                entity.HasOne(d => d.organization).WithMany(p => p.organizations_ref).HasForeignKey(d => d.organization_id).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<people>(entity =>
            {
                entity.HasKey(e => e.person_id);

                entity.Property(e => e.person_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.attribution_name)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.attribution_url)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.created).HasColumnType("date");

                entity.Property(e => e.creator)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.first_use).HasColumnType("date");

                entity.Property(e => e.in_scheme)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.latest_use).HasColumnType("date");

                entity.Property(e => e.license)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.mapping_strategy)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.modified).HasColumnType("date");

                entity.Property(e => e.pref_label)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.primary_topic)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.rightsholder)
                    .HasMaxLength(200)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<people_ref>(entity =>
            {
                entity.HasKey(e => new { e.article_id, e.person_id });

                entity.Property(e => e.article_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.Property(e => e.person_id)
                    .HasMaxLength(100)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.article).WithMany(p => p.people_ref).HasForeignKey(d => d.article_id).OnDelete(DeleteBehavior.Restrict);

                entity.HasOne(d => d.person).WithMany(p => p.people_ref).HasForeignKey(d => d.person_id).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<players>(entity =>
            {
                entity.Property(e => e.id).ValueGeneratedNever();

                entity.Property(e => e.country)
                    .HasMaxLength(3)
                    .HasColumnType("varchar");

                entity.Property(e => e.firstname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");

                entity.Property(e => e.hand)
                    .HasMaxLength(1)
                    .HasColumnType("bpchar");

                entity.Property(e => e.lastname)
                    .HasMaxLength(255)
                    .HasColumnType("varchar");
            });

            modelBuilder.Entity<rankings>(entity =>
            {
                entity.HasOne(d => d.player).WithMany(p => p.rankings).HasForeignKey(d => d.player_id);
            });

            modelBuilder.Entity<salaries>(entity =>
            {
                entity.HasKey(e => new { e.yearid, e.teamid, e.lgid, e.playerid });

                entity.Property(e => e.teamid)
                    .HasMaxLength(3)
                    .HasColumnType("varchar");

                entity.Property(e => e.lgid)
                    .HasMaxLength(2)
                    .HasColumnType("varchar");

                entity.Property(e => e.playerid)
                    .HasMaxLength(9)
                    .HasColumnType("varchar");

                entity.HasOne(d => d.playeridNavigation).WithMany(p => p.salaries).HasForeignKey(d => d.playerid).OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<schools>(entity =>
            {
                entity.HasKey(e => e.schoolid);

                entity.Property(e => e.schoolid)
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
        }

        public virtual DbSet<article_header> article_header { get; set; }
        public virtual DbSet<collegeplaying> collegeplaying { get; set; }
        public virtual DbSet<halloffame> halloffame { get; set; }
        public virtual DbSet<master> master { get; set; }
        public virtual DbSet<organizations> organizations { get; set; }
        public virtual DbSet<organizations_ref> organizations_ref { get; set; }
        public virtual DbSet<people> people { get; set; }
        public virtual DbSet<people_ref> people_ref { get; set; }
        public virtual DbSet<players> players { get; set; }
        public virtual DbSet<rankings> rankings { get; set; }
        public virtual DbSet<salaries> salaries { get; set; }
        public virtual DbSet<schools> schools { get; set; }
    }
}