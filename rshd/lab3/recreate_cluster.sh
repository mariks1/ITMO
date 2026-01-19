TARGET_DIR="uyu39"
F_TBLSPC="nzz47"
S_TBLSPC="ymk58"
BACKUP_DIR="$HOME/lab_3_main_backup"

mkdir $TARGET_DIR
chmod 750 $TARGET_DIR
cd $TARGET_DIR

tar xvf $BACKUP_DIR/base.tar

cd ..
mkdir $F_TBLSPC
chmod 750 $F_TBLSPC
cd $F_TBLSPC
tar xvf $BACKUP_DIR/16384.tar

cd ..
mkdir $S_TBLSPC
chmod 750 $S_TBLSPC
cd $S_TBLSPC
tar xvf $BACKUP_DIR/16389.tar

ln -s ~/$F_TBLSPC ~/$TARGET_DIR/pg_tblspc/16384
ln -s ~/$S_TBLSPC ~/$TARGET_DIR/pg_tblspc/16385

rm -rf ~/$TARGET_DIR/pg_wal/*
