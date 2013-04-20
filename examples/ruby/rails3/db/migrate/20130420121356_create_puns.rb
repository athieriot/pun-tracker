class CreatePuns < ActiveRecord::Migration
  def change
    create_table :puns do |t|
      t.string :description

      t.timestamps
    end
  end
end
