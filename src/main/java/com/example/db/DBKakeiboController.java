package com.example.db;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;


@Controller
@SessionAttributes(value = {"kakeibo"})
public class DBKakeiboController {
	
	@Autowired private KakeiboRepository repository;
	@Autowired private Kakeibo kakeibo ;

	//セッションのやつ
	@ModelAttribute(value="kakeibo")
	private Kakeibo setKakeibo() {
		return this.kakeibo;
	}
	
	//ログイン画面
	@RequestMapping(value="/login")
	public String login(Model model) {
		
		return "kakeibo_login";
	}
	//ログイン出来るか確認
	@RequestMapping(value="/login_check")
	public String login_check(
			//モデルの中のkakeiboという場所からkakeiboを使う為取り出す。
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam String name,
			@RequestParam String pass,
			Model model) {
		//nameとpassが一致するレコードをリストに入れる
		List<Kakeibo> account_list = repository.findByNamePass(name,pass);
		model.addAttribute("account_list",account_list);
		
		if(account_list.size() == 0) {
			model.addAttribute("errorlogin","ユーザー名かパスワードが間違っています");
			return "kakeibo_login";
			
		}else {
			//取り出したkakeiboをIDを含めたkakeiboオブジェクトで上書き
			kakeibo = account_list.get(0);
			//上書きしたkakeiboをモデルに戻す
			model.addAttribute("kakeibo",kakeibo);
			return "redirect:/";
		}
	}
	
	//新規登録画面
	@RequestMapping(value="/sign_up")
	public String signUp(Model model) {
		return "kakeibo_touroku";
	}
	
	//登録完了画面
	@RequestMapping(value="/completion")
	public String completion(
		@Validated @ModelAttribute(value="kakeibo")Kakeibo kakeibo,
		BindingResult result,
		@RequestParam(value="karipass") String karipass,
		Model model) {
		List<Kakeibo> name_list = repository.findByName(kakeibo.getName());
		//model.addAttribute("kakeibo_list", kakeibo_list);
		if(result.hasFieldErrors("name") || result.hasFieldErrors("pass")) {
			return "kakeibo_touroku";
		}
	
		if(name_list.size() >= 1) {
			model.addAttribute("errorname","そのユーザー名は既に使われています");
			return "kakeibo_touroku";
		}
		if(kakeibo.getPass().equals(karipass)) {
			repository.saveAndFlush(kakeibo);
			return "kakeibo_touroku_kanryou";
		}else {
			model.addAttribute("error","パスワードが一致しません");
			return "kakeibo_touroku";
		}
	}
	/////////// 11/14 赤嶺が触った所 ここまで
	
	
	//TOP画面
	@Autowired private Index_viewRepository repository15;
	@RequestMapping(value="/")
	public String index(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			Model model) {
		
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		System.out.println("ユーザーIDは" + user_id);
		List<Index_view> index_view_list = repository15.userSelect(user_id);
		model.addAttribute("index_view_list",index_view_list);
		
		List<Category> category_list = repository11.userSelect(user_id);
		model.addAttribute("category_list",category_list);
		
		List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
		model.addAttribute("incomeview_list", incomeview_list);
				
				
		//支出（category.name）を獲得	
		List<Index_view> category_name_list = repository15.myQueryName(user_id);	
		model.addAttribute("category_name_list",category_name_list);	
		//収入(income.income)を取得	
		List<Index_view> income_income_list = repository15.myQueryIncome(user_id);	
		System.out.println("income_income_listは"+ income_income_list.size());
		
		model.addAttribute("income_income_list",income_income_list);
		
		return "kakeibo_index";
	}
	

		//カテゴリー詳細画面（11/18)　赤嶺変更
		@RequestMapping(value="/kakeibo_category")
		public String category(
				@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
				@RequestParam("cid") Integer cid,
				Model model) {
			
			Integer user_id = kakeibo.getId();
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}
			//amountより情報を取り出す  ( 利用日・支払方法・利用金額・備考・画像表示)
			List<AmountView> amount_list = repository6.categorySelect(cid,user_id);
			model.addAttribute("amount_list", amount_list);

			//カテゴリー名だけ取得
			Optional<Category> categroy_name = repository11.findById(cid);
			//Optionalの場合Listではないので.get()で取り出す必要がある。
			model.addAttribute("categroy_name", categroy_name.get());
			
			//要考え
			List<Income> income_list = repository2.userIncomeSelect(user_id);
			model.addAttribute("income_list", income_list);
			
			return "kakeibo_category";
		}

		//11/18　確認画面
		@RequestMapping(value="/confirm")
		public String confirm(Model model) {
			//金額を足し算
		//	List<AmountView> amount_list = repository6.categorySelect(id);
			//model.addAttribute("amount_list", amount_list);
			//
		
			//categorysetting4より項目を取り出す
			List<Category> category_list = repository11.findAll();
			model.addAttribute("category_list",category_list);
			
			//Amountより各カテゴリの金額を取り出す
			List<Index_view> index_view_list = repository15.findAll();
			model.addAttribute("index_view_list",index_view_list);
			
			
			
			return "kakeibo_confirm";
		}
	
		//11/18　予算画面
		@RequestMapping(value="/timetable")
		public String timetable(Model model) {
			//List<Index_view> index_view_list = repository15.findAll();
			//model.addAttribute("index_view_list",index_view_list);
				
			//List<Category> category_list = repository11.findAll();
			//model.addAttribute("category_list",category_list);
			
			return "kakeibo_timetable";
			}
		
	//設定画面
	@RequestMapping(value="/setting")
	public String setting(Model model) {
		//List<Kakeibo> kakeibo_list = repository.findAll();
		//model.addAttribute("kakeibo_list", kakeibo_list);
		
		return "kakeibo_settei";
	}

	//ログアウト画面 11/21 赤嶺　セッションを消す処理
	@RequestMapping(value="/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "kakeibo_logout";
	}
	
	//銀行口座設定_表示 新崎編集すみ1110
	@RequestMapping(value="/kakeibo_setting1")
	public String setting1(
			HttpServletRequest request,
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			Model model) {
		Integer user_id = kakeibo.getId();
		
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		
		
		List<Bank> bank_list = repository8.userSelect(user_id);
		model.addAttribute("bank_list", bank_list);
	
		return "kakeibo_setting1";
	}
	//銀行口座設定_保管 新崎編集すみ1110
	@Autowired private BankRepository repository8;
	@RequestMapping(value="/kakeibo_setting1_1")
	public String setting1_1(
		@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
		@ModelAttribute(value="bank")Bank bank,Model model,
		@RequestParam(name="bank1")String bank1
		) {
		bank.setBank(bank1);
		repository8.saveAndFlush(bank);
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		List<Bank> bank_list = repository8.userSelect(user_id);
		model.addAttribute("bank_list", bank_list);
		
		return "redirect:kakeibo_setting1";
	}
	// 1125 赤嶺　カテゴリーを消す処理
	@RequestMapping(value="/setting1_delete")
	public String setting1_delete(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam("bid") Integer bid,
			@ModelAttribute(value="bank")Bank bank,
			Model model) {
			Integer user_id = kakeibo.getId();
			
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}
			repository8.deleteById(bid);
			List<Bank> bank_list = repository8.userSelect(user_id);
			model.addAttribute("bank_list", bank_list);
			
			return "redirect:kakeibo_setting1";
		}
	
	//収入カテゴリー設定_表示 嘉数編集すみ1110
	
		//11/16追加　嘉数
		@Autowired private IncomeViewRepository repository2_1;	
		@RequestMapping(value="/kakeibo_setting2")
		public String setting2(
				@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
				Model model) {
			Integer user_id = kakeibo.getId();
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}

	        System.out.println("setting2でのユーザーIDは" + user_id);
			List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
			model.addAttribute("incomeview_list", incomeview_list);
			List<Bank> bank_list = repository8.userSelect(user_id);
			model.addAttribute("bank_list", bank_list);
			
			return "kakeibo_setting2";//ここまで11/16
		}
		
	////収入カテゴリー設定_保管 嘉数編集すみ1110
	@Autowired private IncomeRepository repository2;
	@RequestMapping(value="/kakeibo_setting2_1")
	public String setting2_1(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@ModelAttribute(value="income")Income income,
			@RequestParam(name="income2")String income2,
			Model model) {
		income.setIncome(income2);
		repository2.saveAndFlush(income);
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		List<Income> income_list = repository2.userIncomeSelect(user_id);
		model.addAttribute("income_list", income_list);
		return "redirect:kakeibo_setting2";
	}
	@RequestMapping(value="/setting2_delete")
	public String setting2_delete(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam("iid") Integer iid,
			Model model) {
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		
		repository2.deleteById(iid);
		List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
		model.addAttribute("incomeview_list", incomeview_list);
		List<Bank> bank_list = repository8.userSelect(user_id);
		model.addAttribute("bank_list", bank_list);
		
		return "redirect:kakeibo_setting2";
	}
	//支払方法設定_表示 中山編集スミ1110_1451
	@RequestMapping(value="/kakeibo_setting3")
	public String setting3(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			Model model) {
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		List<Payment> payment_list = repository3.userSelect(user_id);
		model.addAttribute("payment_list", payment_list);
		
		return "kakeibo_setting3";
	}

	//支払方法設定_保管 中山編集スミ1110_1451
	@Autowired private PaymentRepository repository3;
	@RequestMapping(value="/kakeibo_setting3_1")
	public String setting3_1(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@ModelAttribute(value="payment")Payment payment,
			Model model) {
		repository3.saveAndFlush(payment);
		Integer user_id = kakeibo.getId();
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		List<Payment> payment_list = repository3.userSelect(user_id);
		model.addAttribute("payment_list", payment_list);
		
		return "redirect:kakeibo_setting3";
		
	}
	// 1125 赤嶺　カテゴリーを消す処理
	@RequestMapping(value="/setting3_delete")
	public String setting3_delete(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam("pid") Integer pid,
			@ModelAttribute(value="payment")Payment payment,
			Model model) {
			Integer user_id = kakeibo.getId();
			
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}
			repository3.deleteById(pid);
			List<Payment> payment_list = repository3.userSelect(user_id);
			model.addAttribute("payment_list", payment_list);
			
			return "redirect:kakeibo_setting3";
		}
	//1110追加　（insert,delete,update_select)*陳
		@Autowired private Setting4ViewRepository repository4_1;	
		@RequestMapping(value="/kakeibo_setting4")
		//////////  11/16 赤嶺
		public String setting4(
				@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
				Model model) {
			
			Integer user_id = kakeibo.getId();
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}

			
			List<Setting4_View> setting4view_list = repository4_1.userSelect(user_id);
			model.addAttribute("setting4view_list", setting4view_list);
			List<Payment> payment_list = repository3.userSelect(user_id);
			model.addAttribute("payment_list",payment_list);
			return "kakeibo_setting4";
		}
		
	//カテゴリ/予算設定11 1109追加*陳
	@Autowired private CategoryRepository repository11;
	@RequestMapping(value="/kakeibo_setting4_1")
	public String setting4_1(
		@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
		@ModelAttribute(value="category")Category category,
		Model model) {
		repository11.saveAndFlush(category);
		Integer user_id = kakeibo.getId();
		
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		
		List<Category> category_list = repository11.userSelect(user_id);
		model.addAttribute("category_list", category_list);
		
		return "redirect:kakeibo_setting4";
	}
	// 1125 赤嶺　カテゴリーを消す処理
	@RequestMapping(value="/setting4_delete")
	public String setting4_delete(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam("cid") Integer cid,
			@ModelAttribute(value="category")Category category,
			Model model) {
			Integer user_id = kakeibo.getId();
			
			//user_idがnull＝セッションが消えていたらログイン画面に都バス
			if(user_id == null) {
				model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
				return "kakeibo_login";
			}
			repository11.deleteById(cid);
			List<Category> category_list = repository11.userSelect(user_id);
			model.addAttribute("category_list", category_list);
			
			return "redirect:kakeibo_setting4";
		}
	//日にち詳細 1114嘉数
	@Autowired private AmountRepository repository5;
	@Autowired private AmountViewRepository repository6;
	@Autowired private DateRepository dateRepository;
	@RequestMapping(value="/kakeibo_amount")
	public String amount(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@RequestParam("year") String year,
			@RequestParam("month") String month,
			@RequestParam("day") String day,
			Model model) throws ParseException {
		 		model.addAttribute("year", year);
		 		model.addAttribute("month", month);
		 		model.addAttribute("day", day);
				
		 		String date = year + "-" + month + "-" + day ; 
		 		
		 		//Stringの日付をdate型に変換
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date dateSelect = sdFormat.parse(date);
	            System.out.println(date);
		 		System.out.println(dateSelect);
	            model.addAttribute("dateSelect", dateSelect);
	            
	            Integer user_id = kakeibo.getId();
	            
	    		//user_idがnull＝セッションが消えていたらログイン画面に都バス
	    		if(user_id == null) {
	    			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
	    			return "kakeibo_login";
	    		}
	            
				List<AmountView> amountview_list = repository6.dateSelect(dateSelect,user_id);
				model.addAttribute("amountview_list", amountview_list);
				List<Category> category_list = repository11.userSelect(user_id);
				model.addAttribute("category_list",category_list);
				List<Payment> payment_list = repository3.userSelect(user_id);
				model.addAttribute("payment_list", payment_list);
				List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);	
				model.addAttribute("incomeview_list", incomeview_list);	
				//↓日付ごとに出す収入項目	
				List<AmountView> incomeview_list2 = repository6.dateSelectIncome(dateSelect,user_id);	
				model.addAttribute("incomeview_list2", incomeview_list2);
				List<Bank> bank_list = repository8.userSelect(user_id);
				model.addAttribute("bank_list", bank_list);
				
				return "kakeibo_amount";
	}
	////日別レコード編集　1118中山編集スミ
	@RequestMapping(value="/kakeibo_amount_1")
	public String amount_1(
		@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
		@ModelAttribute(value="datedb")Datedb datedb,
		@ModelAttribute(value="amount")Amount amount,
		@RequestParam("year") String year,
		@RequestParam("month") String month,
		@RequestParam("day") String day,
		@RequestParam("image") MultipartFile mf,
		//@RequestParam(name="amount2")String amount2,
		Model model) throws ParseException{
		//amount.setAmount(amount2);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);	
		
		
        Integer user_id = kakeibo.getId();
        System.out.println("amount１でのユーザーIDは" + user_id);
        
		//user_idがnull＝セッションが消えていたらログイン画面に都バス
		if(user_id == null) {
			model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
			return "kakeibo_login";
		}
		
 		//画像アップロード 1117中山編集中
		System.out.println(mf.getOriginalFilename());
		//ファイルの保存場所は必ずstatic内
		try {
			String fileName = mf.getOriginalFilename();
			FileOutputStream fos =
				new FileOutputStream("C:\\pleiades\\workspace\\TeamB_Kakeibo\\src\\main\\resources\\static\\img\\" + fileName);
			fos.write(mf.getBytes());
			fos.close();
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("ファイルのアップロードエラー");
		}
		if(mf == null || mf.getOriginalFilename().equals("")) {
			amount.setImage_path(null);
		} else {
			amount.setImage_path(mf.getOriginalFilename());
		}
		repository5.saveAndFlush(amount);
		
 		String date = year + "-" + month + "-" + day ; 
 		
 		//Stringの日付をdate型に変換
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSelect = sdFormat.parse(date);
 		model.addAttribute("dateSelect", dateSelect);

        
        //選択された日付がdateテーブルに存在するかチェック
		List<Datedb> date_list = dateRepository.findByDate(dateSelect,user_id);
		
		//存在しなければ新しくdateテーブルにレコードを作成
		if(date_list.size() == 0) {
			datedb.setDate(dateSelect);
			datedb.setUser_id(user_id);
			dateRepository.saveAndFlush(datedb);
			
			Integer dateid = datedb.getId();
			//System.out.println("amount２でのユーザーIDは" + user_id);
			
			//上で作ったdateのidをamountにセットして上書き
			amount.setDate_id(dateid);
			repository5.saveAndFlush(amount);
		} else {//存在する場合
			//見つけた
			Integer dateid = date_list.get(0).getId();
			
			amount.setDate_id(dateid);
			repository5.saveAndFlush(amount);
		}
		
		
		List<AmountView> amountview_list = repository6.dateSelect(dateSelect,user_id);
		model.addAttribute("amountview_list", amountview_list);
		List<Category> category_list = repository11.userSelect(user_id);
		model.addAttribute("category_list",category_list);
		List<Payment> payment_list = repository3.userSelect(user_id);
		model.addAttribute("payment_list", payment_list);
		List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
		model.addAttribute("incomeview_list", incomeview_list);
		List<Bank> bank_list = repository8.userSelect(user_id);
		model.addAttribute("bank_list", bank_list);
					
		return "redirect:kakeibo_amount?year=" + year + "&month=" + month + "&day=" + day;
	}
	
	//送信データが収入の時の処理
	@RequestMapping(value="/kakeibo_amount_2")	
	public String amount_2(	
	@ModelAttribute(value="kakeibo")Kakeibo kakeibo,	
	@ModelAttribute(value="datedb")Datedb datedb,	
	@ModelAttribute(value="amount")Amount amount,	
	@ModelAttribute(value="income")Income income,	
	@RequestParam("year") String year,	
	@RequestParam("month") String month,	
	@RequestParam("day") String day,
	//@RequestParam(name="amount2")String amount2,	
	Model model) throws ParseException{	
	//amount.setAmount(amount2);	
	model.addAttribute("year", year);	
	model.addAttribute("month", month);	
	model.addAttribute("day", day);	
	Integer user_id = kakeibo.getId();	
	System.out.println("amount１でのユーザーIDは" + user_id);	
	
	//user_idがnull＝セッションが消えていたらログイン画面に都バス
	if(user_id == null) {
		model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
		return "kakeibo_login";
	}
	
	String date = year + "-" + month + "-" + day ;
		//Stringの日付をdate型に変換
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateSelect = sdFormat.parse(date);
		model.addAttribute("dateSelect", dateSelect);

    
    //選択された日付がdateテーブルに存在するかチェック
	List<Datedb> date_list = dateRepository.findByDate(dateSelect,user_id);
	
	//存在しなければ新しくdateテーブルにレコードを作成
	if(date_list.size() == 0) {
		datedb.setDate(dateSelect);
		datedb.setUser_id(user_id);
		dateRepository.saveAndFlush(datedb);
		
		Integer dateid = datedb.getId();
		//System.out.println("amount２でのユーザーIDは" + user_id);
		
		//上で作ったdateのidをamountにセットして上書き
		amount.setDate_id(dateid);
		repository5.saveAndFlush(amount);
	} else {//存在する場合
		//見つけた
		Integer dateid = date_list.get(0).getId();
		
		amount.setDate_id(dateid);
		repository5.saveAndFlush(amount);
	}
	
	
	List<AmountView> amountview_list = repository6.dateSelect(dateSelect,user_id);
	model.addAttribute("amountview_list", amountview_list);
	List<Category> category_list = repository11.userSelect(user_id);
	model.addAttribute("category_list",category_list);
	List<Payment> payment_list = repository3.userSelect(user_id);
	model.addAttribute("payment_list", payment_list);
	List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
	model.addAttribute("incomeview_list", incomeview_list);
	List<Bank> bank_list = repository8.userSelect(user_id);
	model.addAttribute("bank_list", bank_list);
				
	return "redirect:kakeibo_amount?year=" + year + "&month=" + month + "&day=" + day;
}
	
		////日別レコード削除 1117中山編集スミ
		@RequestMapping(value="/delete")
		public String delete(
			@ModelAttribute(value="kakeibo")Kakeibo kakeibo,
			@ModelAttribute(value="amount")Amount amount,	
			@RequestParam("amount_id") Integer amount_id,
			@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,
			@RequestParam("day") Integer day,
			Model model) throws ParseException {
				

				Integer user_id = kakeibo.getId();
				//user_idがnull＝セッションが消えていたらログイン画面に都バス
				if(user_id == null) {
					model.addAttribute("sessionError","前回のログインから時間が経っている為、ログアウトしました\nログインし直してください。");
					return "kakeibo_login";
				}
				repository5.deleteById(amount_id);
				String date = year + "-" + month + "-" + day ;
				//Stringの日付をdate型に変換
			    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			    Date dateSelect = sdFormat.parse(date);
				model.addAttribute("dateSelect", dateSelect);
				List<AmountView> amountview_list = repository6.dateSelect(dateSelect,user_id);
				model.addAttribute("amountview_list", amountview_list);
				List<Category> category_list = repository11.userSelect(user_id);
				model.addAttribute("category_list",category_list);
				List<Payment> payment_list = repository3.userSelect(user_id);
				model.addAttribute("payment_list", payment_list);
				List<IncomeView> incomeview_list = repository2_1.userIncomeSelect(user_id);
				model.addAttribute("incomeview_list", incomeview_list);
				List<Bank> bank_list = repository8.userSelect(user_id);
				model.addAttribute("bank_list", bank_list);
		
		return "redirect:kakeibo_amount?year=" + year + "&month=" + month + "&day=" + day;
			//return "redirect:/";
		}
}

