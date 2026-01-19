PGDATA_NEW="$HOME/uyu39_new"
mkdir -p "$PGDATA_NEW" && chmod 700 "$PGDATA_NEW"

tar -xvf "$HOME/lab_3_main_backup/base.tar" -C "$PGDATA_NEW"

mkdir -p "$HOME/nzz47" && chmod 700 "$HOME/nzz47"
tar -xvf "$HOME/lab_3_main_backup/16384.tar" -C "$HOME/nzz47"

mkdir -p "$HOME/ymk58" && chmod 700 "$HOME/ymk58"
tar -xvf "$HOME/lab_3_main_backup/16389.tar" -C "$HOME/ymk58"

ln -s "$HOME/nzz47" "$PGDATA_NEW/pg_tblspc/16384"
ln -s "$HOME/ymk58" "$PGDATA_NEW/pg_tblspc/16389"
