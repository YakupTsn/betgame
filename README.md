# betgame
>  Projemizi oluştururken amacımız admin-server tarafından oluşturulan bülten(betround)
, kullanıcı tarından tahmin edip siteme kaydedip,
daha sonra bülten sonuçlandığında eğer kullanıcı kuponları bülten sonucuna uyuyorsa 
başarılı yada başarısız olarak kullanıcı mail adresine sonucu atmak ve database den güncellemektir.
 Kısaca iddaa sistemine benzer bir sistem yapmaya çalıştım.
    Projemizde 3 controller bulunuyor;
# AccountController
    /account/register 
->method adı:  <p> register(@RequestBody RegisterRequest request) </p>
isteğimiz apimize kullanıcı kaydı oluşturmamızı sağlar.
permitall bir istektir,herkese açık.    
   
     /account/login
->method adı:  <p>  login(@RequestBody @Valid LoginRequest request) </p>
apimize login ve token kontrolü sağlar.
permitall bir istektir,herkese açık.  
    
    /account/me
->method adı:  <p>  getMe() </p>
giriş yapan kullanıcıların bilgilerini getirir.
IsAuthentificated bir istektir, login olan herkese açık.
    
    /account/forgotpassword
->method adı:  <p> putPassword(@RequestParam String mail) </p>
mail adresi girilen kullanıcıya rastgele bir parola üretip database de
günceller ve kullanıcıya yeni parolasını mail olarak gönderir.
permitall bir istektir,herkese açık.
# BetRoundController
    /betround/save/server
->method adı:  <p>  saveBetRound(@RequestBody BetRoundRequest betRoundRequest)  </p>
-admin login olduysa bülten ekleme yapar database de bet 
rolü SERVER  olur, oyun listesi ve bülten adı gibi istekde yer 
alan bilgileri işaretler ,bülten oluştururken maç sonucu giremez ve bülteni oluşturur.
-user login olduysa bülten ile aynı serverBetRoundId sahip olmalı ve 
gameList teki serverId de bültendeki her game karşılık gelen Id olmalıdır.
 Aslında tek post isteğinden 2 işlem yapmış olur.Hem bülten kaydetmemize yarar ,
hemde kullanıcı için kupon oluşturmamıza yarar.
IsAuthentificated bir istektir, login olan herkese açık.
    
    /bedround
->method adı:  <p>  saveGameResult(@RequestBody GameResult gameResult) </p>
bu patchmapping işleminde bültenimizi oluştururken boş bıraktığımız yani
maç sonuçlarımızı girmemize ,update etmemizi sağlar.
OnlyAdmin bir istektir sadece adminlere açık.

    /betround/update/server
->method adı:  <p>  saveBetRoundIsSuccessAndSendMail(@RequestParam Long betRoundId) </p>
verilen betRoundID ile betRound u bulur ve bu betRound üzerinden
serverBetRoundId leri aynı olan betRoundları betRole ü
user olanları(kuponları) getirir,daha sonra bu kuponları betround daki
gameListteki sonuçlar (DRAW,FIRST,SECOND) doğrumu kontrol yapar,
betStatus leri günceller ve betRound lardaki ownerId ile kullanıcıları bulup
onların mail adreslerine sonuçları gönderir.
OnlyAdmin bir istektir sadece adminlere açık.

    /betround
->method adı:  <p>  getAllBetRounds(@Nullable @RequestParam PlayableStatus playableStatus, Pageable pageable) </p>
verilen statüye göre bültenleri listeleme yapar,
eğer PlayableStatus boş geçilirse bütün bültenleri getirir.
IsAuthentificated bir istektir, login olan herkese açık.


#   UserController
    /users/change/password
->method adı:  <p>  changePassword(@RequestParam String newPassword) </p>
apiye giriş yapan kullanıcının şifresini günceller.
IsAuthentificated bir istektir, login olan herkese açık.

    /users/changedrole
->method adı:  <p> changedRole(@RequestParam String userId, @RequestParam UserRole userRole) </p>
id si ve değişecek rolü verilen kullanıcının rolünü günceller.
OnlyAdmin bir istektir sadece adminlere açık.
    /users
->method adı:  <p> getAllUsers(Pageable pageable) </p>
kayıtlı tüm kullanıcıları getirir.
OnlyAdmin bir istektir sadece adminlere açık.

    /users/betrounds
->method adı:  <p>  getAllUserBets(Pageable pageable) </p>
login olan kullanıcının kuponlarını getirir.
IsAuthentificated bir istektir, login olan herkese açık.

    