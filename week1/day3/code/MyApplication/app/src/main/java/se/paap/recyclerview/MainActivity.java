package se.paap.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> strings = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            strings.add("Hejsan " + i);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ExampleAdapter adapter = new ExampleAdapter(strings);

        adapter.setOnItemClickedListener(new ExampleAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(String string) {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {

    }

    private static class ExampleAdapter extends RecyclerView.Adapter<ExampleViewHolder> {

        private final List<String> strings;
        private OnItemClickedListener onItemClickedListener;

        ExampleAdapter(List<String> strings) {
            this.strings = strings;
        }

        void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }

        @Override
        public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View view = inflater.inflate(R.layout.list_item_string, parent, false);

            ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
            exampleViewHolder.setOnItemClickedListener(new ExampleViewHolder.OnItemClickedListener() {
                @Override
                public void onItemClicked(int position) {
                    if(onItemClickedListener != null) {
                        String string = strings.get(position);
                        onItemClickedListener.onItemClicked(string);
                    }
                }
            });

            return exampleViewHolder;
        }

        @Override
        public void onBindViewHolder(ExampleViewHolder holder, int position) {
            final String string = this.strings.get(position);
            holder.bindView(string);
        }

        @Override
        public int getItemCount() {
            return this.strings.size();
        }

        interface OnItemClickedListener {
            void onItemClicked(String string);
        }
    }

    private static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private OnItemClickedListener onItemClickedListener;

        ExampleViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickedListener != null) {
                        int position = getAdapterPosition();
                        onItemClickedListener.onItemClicked(position);
                    }
                }
            });

            textView = (TextView) itemView.findViewById(R.id.text_view);
        }

        void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }

        void bindView(String string) {
            textView.setText(string);
        }

        interface OnItemClickedListener {
            void onItemClicked(int position);
        }
    }
}
